import {Component, Input, OnInit} from "@angular/core";
import {Account} from "../../models/Account";
import {Role} from "../../models/Role";

@Component({
  selector: 'app-account-form',
  templateUrl: './account-form.component.html',
  styleUrls: ['./account-form.component.css']
})
export class AccountFormComponent implements OnInit {

  @Input()
  account: Account;

  @Input()
  submitButtonName: string;

  @Input()
  showSubmitButton: boolean = true;

  roles: Role[] = Object.keys(Role).map(role => Role[role]);

  constructor() { }

  ngOnInit() {
  }

  hasRole(role: Role) {
    return this.account.roles.indexOf(role) > -1;
  }

  onChange(role: Role) {
    const index = this.account.roles.indexOf(role);
    if (index > -1) {
      this.account.roles.splice(index, 1);
    }
    else {
      this.account.roles.push(role);
    }
  }

}
