import {Component, Input, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {User} from "../../models/User";
import {UserService} from "../../services/user.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

  @Input()
  user: User;

  submitButtonName: string = "Save";

  constructor(private activatedRoute: ActivatedRoute,
              private userService: UserService,
              private location: Location) {
  }

  ngOnInit() {
    this.activatedRoute.data.subscribe((data: {user: User}) => {this.user = data.user});
  }

  onSubmit() {
    this.userService.updateUser(this.user);
    this.location.back();
  }

}
