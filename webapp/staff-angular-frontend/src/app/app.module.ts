import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";

import {AppComponent} from "./app.component";
import {NavbarComponent} from "./components/navbar/navbar.component";
import {FooterComponent} from "./components/footer/footer.component";
import {UserListComponent} from "./components/user-list/user-list.component";
import {UserComponent} from "./components/user/user.component";
import {AddUserComponent} from "./components/add-user/add-user.component";
import {EditUserComponent} from "./components/edit-user/edit-user.component";
import {UserService} from "./services/user.service";
import {AccountListComponent} from "./components/account-list/account-list.component";
import {AccountComponent} from "./components/account/account.component";
import {AccountService} from "./services/account.service";
import {AppRoutingModule} from "./app-routing.module";
import {DashboardComponent} from "./components/dashboard/dashboard.component";


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    FooterComponent,
    UserListComponent,
    UserComponent,
    AddUserComponent,
    EditUserComponent,
    AccountListComponent,
    AccountComponent,
    DashboardComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [
    UserService,
    AccountService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
