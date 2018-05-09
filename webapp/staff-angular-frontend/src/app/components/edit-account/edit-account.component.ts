import {Component, Input, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {AccountService} from "../../services/account.service";
import {Location} from "@angular/common";
import {Account} from "../../models/Account";

@Component({
  selector: 'app-edit-account',
  templateUrl: './edit-account.component.html',
  styleUrls: ['./edit-account.component.css']
})
export class EditAccountComponent implements OnInit {

  @Input()
  account: Account;

  constructor(private activatedRoute: ActivatedRoute,
              private accountService: AccountService,
              private location: Location) {
  }

  ngOnInit() {
    const id = parseInt(this.activatedRoute.snapshot.paramMap.get('id'));
    this.accountService.getAccountById(id).subscribe(account => this.account = account);
  }

  onSubmit() {
    this.accountService.updateAccount(this.account);
    this.location.back();
  }

}
