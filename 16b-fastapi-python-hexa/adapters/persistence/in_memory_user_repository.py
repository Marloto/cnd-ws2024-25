from typing import Optional, Dict
from application.user_repository import UserRepository
from domain.models.user import User
from .dpos.user_dpo import UserDPO

class InMemoryUserRepository(UserRepository):
    def __init__(self):
        self.users: Dict[str, UserDPO] = {}

    async def save_user(self, user: User) -> User:
        user_dpo = UserDPO.from_domain(user)
        self.users[user_dpo.id] = user_dpo
        return user_dpo.to_domain()

    async def find_user_by_id(self, id: str) -> Optional[User]:
        user_dpo = self.users.get(id)
        return user_dpo.to_domain() if user_dpo else None