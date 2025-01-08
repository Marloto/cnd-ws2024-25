from dataclasses import dataclass
from domain.models.user import User
from datetime import datetime
from typing import Optional

@dataclass
class UserDPO:
    id: str
    name: str
    email: str
    password_hash: str
    user_role: str
    last_login: Optional[datetime]

    @classmethod
    def from_domain(cls, user: User) -> 'UserDPO':
        return cls(
            id=user.id,
            name=user.name,
            email=user.email,
            password_hash=user.password_hash,
            user_role=user.role,
            last_login=user.last_login
        )

    def to_domain(self) -> User:
        return User(
            id=self.id,
            name=self.name,
            email=self.email,
            password_hash=self.password_hash,
            role=self.user_role,
            last_login=self.last_login
        )