import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {Account} from "../../models/Account";

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  @Input()
  account: Account;

  @Output()
  edit: EventEmitter<Account> = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  onEdit() {
    this.edit.emit(this.account);
  }

}
