import {Component, Input, OnInit} from "@angular/core";
import {Account} from "../../models/Account";

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  @Input()
  account: Account;

  constructor() { }

  ngOnInit() {
  }

}
