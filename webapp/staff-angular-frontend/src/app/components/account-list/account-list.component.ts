import {Component, OnInit} from "@angular/core";
import {AccountService} from "../../services/account.service";
import {Account} from "../../models/Account";

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})
export class AccountListComponent implements OnInit {

  accounts: Account[];

  constructor(private accountService: AccountService) { }

  ngOnInit() {
    this.accountService.getAccounts().subscribe(accounts => this.accounts = accounts);
  }

  edit(account: Account) {
    console.log("edit");
    console.log(account);
  }

}
