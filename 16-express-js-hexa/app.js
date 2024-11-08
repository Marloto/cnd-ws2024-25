import {InMemoryUserRepository} from './adapters/persistence/inMemoryUserRepository.js'
import {ExpressAdapter} from './adapters/web/expressAdapter.js'
import {UserServiceImpl} from './application/userService.js'

export class App {
    constructor() {
        // Konfiguration der Abhängigkeiten
        const userRepository = new InMemoryUserRepository();
        const userService = new UserServiceImpl(userRepository);
        
        // Web-Adapter initialisieren
        this.webAdapter = new ExpressAdapter(userService);
    }

    start(port) {
        this.webAdapter.start(port);
    }
}
