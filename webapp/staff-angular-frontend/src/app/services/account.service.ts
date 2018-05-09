import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {Account} from "../models/Account";
import {of} from "rxjs/observable/of";
import {Role} from "../models/Role";

@Injectable()
export class AccountService {

  accounts: Account[] = [
    {
      id: 0,
      username: "myUsername",
      enabled: true,
      createdOn: new Date(),
      roles: [Role.DELIVERYPERSON, Role.REGISTERED_CUSTOMER]
    }
  ];

  constructor() { }

  getAccounts(): Observable<Account[]> {
    return of(this.accounts);
  }

  getAccountById(id: number): Observable<Account> {
    return of(this.accounts[id]);
  }

  updateAccount(account: Account): void {
    console.log("account updated");
    console.log(account);
  }

  changePassword(accountId: number, password: string): void {
    console.log("changed password");
    console.log(accountId);
    console.log(password);
  }
}
