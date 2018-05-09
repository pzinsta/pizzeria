import {Account} from "./Account";

export interface User {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: string;
  account?: Account;
}
