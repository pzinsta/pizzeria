import {Component, Input, OnInit} from "@angular/core";
import {UserService} from "../../services/user.service";
import {User} from "../../models/User";
import {forkJoin} from "rxjs/observable/forkJoin";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: User[];
  p: number = 1;
  loading: boolean;
  total: number;

  @Input()
  usersPerPage: number = 3;

  constructor(private userService: UserService) {

  }

  ngOnInit() {
    this.userService.getUsers().subscribe(users => this.users = users);
  }

  onPageChange(page: number) {
    this.loading = true;
    const start: number = (this.p - 1) * this.usersPerPage;
    const end: number = start + this.usersPerPage;
    forkJoin(this.userService.getTotal(), this.userService.getUsersInRange(start, end))
      .subscribe(([total, users]) => {
        this.total = total;
        this.users = users;
        this.p = page;
        this.loading = false;
      });
  }
}
