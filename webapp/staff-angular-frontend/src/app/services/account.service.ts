import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {Account} from "../models/Account";
import {of} from "rxjs/observable/of";

@Injectable()
export class AccountService {

  constructor() { }

  getAccounts(): Observable<Account[]> {
    return of([
      {
        id: 0,
        username: "myUsername",
        enabled: true,
        createdOn: new Date()
      }
    ]);
  }

}
