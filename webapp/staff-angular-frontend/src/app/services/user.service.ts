import {Injectable} from "@angular/core";
import {User} from "../models/User";
import {Observable} from "rxjs";
import {of} from "rxjs/observable/of";

@Injectable()
export class UserService {

  constructor() { }

  // todo replace with rest call
  getUsers() : Observable<User[]> {
    return of([
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
      }
    ]);
  }
}
