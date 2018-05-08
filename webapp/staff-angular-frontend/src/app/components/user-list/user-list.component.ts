import {Component, OnInit} from "@angular/core";
import {UserService} from "../../services/user.service";
import {User} from "../../models/User";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: User[];

  constructor(private userService: UserService) {

  }

  ngOnInit() {
    this.userService.getUsers().subscribe(users => this.users = users);
  }

  onEdit(user: User) {
    console.log("edit");
    console.log(user);
  }

  onRemove(user: User) {
    console.log("remove");
    console.log(user);
  }
}
