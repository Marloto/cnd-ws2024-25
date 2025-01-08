from abc import ABC, abstractmethod
from domain.models.user import User
from typing import Optional

class UserRepository(ABC):
    @abstractmethod
    async def save_user(self, user: User) -> User:
        pass

    @abstractmethod
    async def find_user_by_id(self, id: str) -> Optional[User]:
        pass