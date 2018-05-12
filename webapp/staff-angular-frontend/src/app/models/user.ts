import {Account} from "./account";

export interface User {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: string;
  account?: Account;
}
