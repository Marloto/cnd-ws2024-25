from abc import ABC, abstractmethod
from .models.user import User

class UserService(ABC):
    @abstractmethod
    async def create_user(self, name: str, email: str, password: str) -> User:
        pass

    @abstractmethod
    async def get_user_by_id(self, id: str) -> User:
        pass

    @abstractmethod
    def validate_user(self, user: User) -> None:
        pass