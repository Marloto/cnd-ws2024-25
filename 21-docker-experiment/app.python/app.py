# app.py
from fastapi import FastAPI, Response, HTTPException
from fastapi.responses import JSONResponse
from redis import Redis
from pymongo import MongoClient
from datetime import datetime
import socket
import time
import os

app = FastAPI()

# Environment variables
redis_host = os.getenv('REDIS_HOST', 'localhost')
redis_port = int(os.getenv('REDIS_PORT', 6379))
mongo_url = os.getenv('MONGO_URL', 'mongodb://localhost:27017')

# Redis connection
redis = Redis(host=redis_host, port=redis_port, decode_responses=True)

# MongoDB connection
mongo_client = MongoClient(mongo_url)
db = mongo_client.stresstest

# Modifizierbarer Health Status
health_status = True

@app.get("/")
async def root():
    return {
        "status": "ok",
        "hostname": socket.gethostname(),
        "timestamp": datetime.now().isoformat()
    }

@app.get("/stress/cpu/{seconds}")
async def stress_cpu(seconds: int):
    start = time.time()
    while time.time() - start < seconds:
        _ = 123.456 * 789.012
    
    return {
        "message": f"CPU stress test completed after {seconds} seconds",
        "hostname": socket.gethostname()
    }

@app.get("/counter")
async def counter():
    try:
        count = redis.incr('visitor_count')
        return {
            "count": count,
            "hostname": socket.gethostname()
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/hello")
async def hello():
    return {
        "message": "Hello, World!",
        "hostname": socket.gethostname()
    }

@app.post("/log")
async def log(data: dict):
    try:
        data["timestamp"] = datetime.now()
        result = db.logs.insert_one(data)
        return {
            "inserted": str(result.inserted_id),
            "hostname": socket.gethostname()
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/crash")
async def crash():
    def exit_app():
        time.sleep(1)
        os._exit(1)
        
    import threading
    threading.Thread(target=exit_app).start()
    
    return {
        "message": "Crashing in 1 second...",
        "hostname": socket.gethostname()
    }


@app.get("/health")
async def health():
    if health_status:
        return {"status": "ok"}
    else:
        return JSONResponse(
            status_code=503,
            content={"status": "error"}
        )

@app.get("/toggle-health")
async def health():
    global health_status
    health_status = not health_status
    return Response(status_code=204)