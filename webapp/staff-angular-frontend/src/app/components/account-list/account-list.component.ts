import {Component, Input, OnInit} from "@angular/core";
import {AccountService} from "../../services/account.service";
import {Account} from "../../models/Account";
import {forkJoin} from "rxjs/observable/forkJoin";

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})
export class AccountListComponent implements OnInit {

  accounts: Account[];
  p: number = 1;
  loading: boolean;
  total: number;

  @Input()
  accountsPerPage: number = 3;


  constructor(private accountService: AccountService) { }

  ngOnInit() {
    this.accountService.getAccounts().subscribe(accounts => this.accounts = accounts);
  }

  onPageChange(page: number) {
    this.loading = true;
    const start: number = (this.p - 1) * this.accountsPerPage;
    const end: number = start + this.accountsPerPage;
    forkJoin(this.accountService.getTotal(), this.accountService.getAccountsInRange(start, end))
      .subscribe(([total, accounts]) => {
        this.total = total;
        this.accounts = accounts;
        this.p = page;
        this.loading = false;
      });
  }

}
