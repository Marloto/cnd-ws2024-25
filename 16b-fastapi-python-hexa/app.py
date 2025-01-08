from adapters.persistence.in_memory_user_repository import InMemoryUserRepository
from adapters.web.fast_api_adapter import FastAPIAdapter
from application.user_service_impl import UserServiceImpl
import uvicorn

class App:
    def __init__(self):
        # Configure dependencies
        user_repository = InMemoryUserRepository()
        user_service = UserServiceImpl(user_repository)
        
        # Initialize web adapter
        self.web_adapter = FastAPIAdapter(user_service)
        self.app = self.web_adapter.app

    def start(self, port: int):
        uvicorn.run(self.app, host="0.0.0.0", port=port)