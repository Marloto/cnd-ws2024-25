from fastapi import FastAPI, HTTPException
from domain.user_service import UserService
from .dtos.user_dto import UserDTO
from .dtos.requests import CreateUserRequest

class FastAPIAdapter:
    def __init__(self, user_service: UserService):
        self.user_service = user_service
        self.app = FastAPI()
        self.setup_routes()

    def setup_routes(self):
        @self.app.post("/users", response_model=UserDTO)
        async def create_user(user_data: CreateUserRequest):
            try:
                user = await self.user_service.create_user(
                    name=user_data.name,
                    email=str(user_data.email),
                    password=user_data.password
                )
                return UserDTO.from_domain(user)
            except ValueError as e:
                raise HTTPException(status_code=400, detail=str(e))

        @self.app.get("/users/{user_id}", response_model=UserDTO)
        async def get_user(user_id: str):
            try:
                user = await self.user_service.get_user_by_id(user_id)
                return UserDTO.from_domain(user)
            except ValueError as e:
                raise HTTPException(status_code=404, detail=str(e))