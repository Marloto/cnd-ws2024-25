from domain.user_service import UserService
from domain.models.user import User
from application.user_repository import UserRepository
from datetime import datetime
import uuid

class UserServiceImpl(UserService):
    def __init__(self, user_repository: UserRepository):
        self.user_repository = user_repository

    async def create_user(self, name: str, email: str, password: str) -> User:
        # In a real application, password should be properly hashed
        user = User(
            id=str(uuid.uuid4()),
            name=name,
            email=email,
            password_hash=password,  # simplified for demo
            role='USER',
            last_login=None
        )
        self.validate_user(user)
        return await self.user_repository.save_user(user)

    async def get_user_by_id(self, id: str) -> User:
        user = await self.user_repository.find_user_by_id(id)
        if not user:
            raise ValueError('User not found')
        return user

    def validate_user(self, user: User) -> None:
        if len(user.name) < 2:
            raise ValueError('Name must be at least 2 characters long')