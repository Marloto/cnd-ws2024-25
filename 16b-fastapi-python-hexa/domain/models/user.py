from dataclasses import dataclass
from datetime import datetime
from typing import Optional

@dataclass
class User:
    id: str
    name: str
    email: str
    password_hash: str
    role: str
    last_login: Optional[datetime]