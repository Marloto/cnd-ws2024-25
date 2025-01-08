from domain.models.user import User
from pydantic import BaseModel, EmailStr

class UserDTO(BaseModel):
    id: str
    name: str
    email: EmailStr

    @classmethod
    def from_domain(cls, user: User) -> 'UserDTO':
        return cls(
            id=user.id,
            name=user.name,
            email=user.email
        )
