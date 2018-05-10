import {Injectable} from "@angular/core";
import {User} from "../models/User";
import {Observable} from "rxjs";

@Injectable()
export class UserService {

  users: User[] = [
    {
      id: 0,
      email: 'jack@gmail.com',
      firstName: 'Jack',
      lastName: 'Smith',
      phoneNumber: '12345678'
    },
    {
      id: 1,
      email: 'john@gmail.com',
      firstName: 'John',
      lastName: 'Mellon',
      phoneNumber: '878654321'
    },
    {
      id: 0,
      email: 'jack@gmail.com',
      firstName: 'Jack',
      lastName: 'Smith',
      phoneNumber: '12345678'
    },
    {
      id: 1,
      email: 'john@gmail.com',
      firstName: 'John',
      lastName: 'Mellon',
      phoneNumber: '878654321'
    },
    {
      id: 0,
      email: 'jack@gmail.com',
      firstName: 'Jack',
      lastName: 'Smith',
      phoneNumber: '12345678'
    },
    {
      id: 1,
      email: 'john@gmail.com',
      firstName: 'John',
      lastName: 'Mellon',
      phoneNumber: '878654321'
    },
    {
      id: 0,
      email: 'jack@gmail.com',
      firstName: 'Jack',
      lastName: 'Smith',
      phoneNumber: '12345678'
    },
    {
      id: 1,
      email: 'john@gmail.com',
      firstName: 'John',
      lastName: 'Mellon',
      phoneNumber: '878654321'
    },
    {
      id: 0,
      email: 'jack@gmail.com',
      firstName: 'Jack',
      lastName: 'Smith',
      phoneNumber: '12345678'
    },
    {
      id: 1,
      email: 'john@gmail.com',
      firstName: 'John',
      lastName: 'Mellon',
      phoneNumber: '878654321'
    },
    {
      id: 0,
      email: 'jack@gmail.com',
      firstName: 'Jack',
      lastName: 'Smith',
      phoneNumber: '12345678'
    },
    {
      id: 1,
      email: 'john@gmail.com',
      firstName: 'John',
      lastName: 'Mellon',
      phoneNumber: '878654321'
    },
    {
      id: 0,
      email: 'jack@gmail.com',
      firstName: 'Jack',
      lastName: 'Smith',
      phoneNumber: '12345678'
    },
    {
      id: 1,
      email: 'john@gmail.com',
      firstName: 'John',
      lastName: 'Mellon',
      phoneNumber: '878654321'
    },
    {
      id: 0,
      email: 'jack@gmail.com',
      firstName: 'Jack',
      lastName: 'Smith',
      phoneNumber: '12345678'
    },
    {
      id: 1,
      email: 'john@gmail.com',
      firstName: 'John',
      lastName: 'Mellon',
      phoneNumber: '878654321'
    },
    {
      id: 0,
      email: 'jack@gmail.com',
      firstName: 'Jack',
      lastName: 'Smith',
      phoneNumber: '12345678'
    },
    {
      id: 1,
      email: 'john@gmail.com',
      firstName: 'John',
      lastName: 'Mellon',
      phoneNumber: '878654321'
    },
    {
      id: 0,
      email: 'jack@gmail.com',
      firstName: 'Jack',
      lastName: 'Smith',
      phoneNumber: '12345678'
    },
    {
      id: 1111,
      email: 'john@gmail.com',
      firstName: 'John',
      lastName: 'Mellon',
      phoneNumber: '878654321'
    }
  ];

  constructor() {
  }

  // todo replace with rest call
  getUsers(): Observable<User[]> {
    return Observable.of(this.users).delay(1000);
  }

  getUsersInRange(start: number, end: number): Observable<User[]> {
    return Observable.of(this.users.slice(start, end)).delay(1000);
  }

  getTotal(): Observable<number> {
    return Observable.of(this.users.length).delay(1000);
  }

  getUserById(id: number): Observable<User> {
    return Observable.of(this.users[id]).delay(2000);
  }

  updateUser(user: User): void {
    console.log(user);
    console.log("updated");
  }

  addUser(user: User): void {
    console.log(user);
    console.log("added");
  }
}
