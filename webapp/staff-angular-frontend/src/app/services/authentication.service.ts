import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Role} from "../models/role";
import {Observable} from "rxjs/Observable";
import {map} from "rxjs/operators";
import {isNullOrUndefined} from "util";

@Injectable()
export class AuthenticationService {

  authenticationUrl: string = "http://localhost:8081/pizzeria/authentication";
  logoutUrl: string = "/logout";

  username: string = "";
  password: string = "";

  rolePrefix: string = "ROLE_";

  redirectUrl: string;

  constructor(private httpClient: HttpClient) { }

  login(username: string, password: string): Observable<boolean>  {
    this.username = username;
    this.password = password;
    const headers = new HttpHeaders({
      authorization : 'Basic ' + btoa(username + ':' + password)
    });
    console.log("login");
    return this.httpClient.get(this.authenticationUrl, {headers: headers})
      .pipe(
        map(response => !isNullOrUndefined(response['name']))
      );
  }

  logout(): void {
    this.httpClient.post(this.logoutUrl, {}).subscribe();
  }

  hasRole(role: Role): Observable<boolean> {
    const headers = new HttpHeaders({
      authorization : 'Basic ' + btoa(this.username + ':' + this.password)
    });
    return this.httpClient.get(this.authenticationUrl, {}).pipe(
      map(response => response['roles'].indexOf(this.rolePrefix + role) > -1)
    );
  }
}
