import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Account} from "../models/Account";
import {Role} from "../models/role";

@Injectable()
export class AccountService {

  accounts: Account[] = [
    {
      id: 0,
      username: "myUsername",
      enabled: true,
      createdOn: new Date(),
      roles: [Role.DELIVERYPERSON, Role.REGISTERED_CUSTOMER]
    },
    {
      id: 0,
      username: "myUsername",
      enabled: true,
      createdOn: new Date(),
      roles: [Role.DELIVERYPERSON, Role.REGISTERED_CUSTOMER]
    },
    {
      id: 0,
      username: "myUsername",
      enabled: true,
      createdOn: new Date(),
      roles: [Role.DELIVERYPERSON, Role.REGISTERED_CUSTOMER]
    },
    {
      id: 0,
      username: "myUsername",
      enabled: true,
      createdOn: new Date(),
      roles: [Role.DELIVERYPERSON, Role.REGISTERED_CUSTOMER]
    },
    {
      id: 0,
      username: "myUsername",
      enabled: true,
      createdOn: new Date(),
      roles: [Role.DELIVERYPERSON, Role.REGISTERED_CUSTOMER]
    },
    {
      id: 0,
      username: "myUsername",
      enabled: true,
      createdOn: new Date(),
      roles: [Role.DELIVERYPERSON, Role.REGISTERED_CUSTOMER]
    },
    {
      id: 0,
      username: "myUsername",
      enabled: true,
      createdOn: new Date(),
      roles: [Role.DELIVERYPERSON, Role.REGISTERED_CUSTOMER]
    },
    {
      id: 111,
      username: "myUsername",
      enabled: true,
      createdOn: new Date(),
      roles: [Role.DELIVERYPERSON, Role.REGISTERED_CUSTOMER]
    }
  ];

  constructor() { }

  getAccounts(): Observable<Account[]> {
    return Observable.of(this.accounts).delay(1000);
  }

  getAccountById(id: number): Observable<Account> {
    return Observable.of(this.accounts[id]).delay(1000);
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

  getTotal(): Observable<number> {
    return Observable.of(this.accounts.length).delay(1234);
  }

  getAccountsInRange(start: number, end: number): Observable<Account[]> {
    return Observable.of(this.accounts.slice(start, end)).delay(1000);
  }
}
