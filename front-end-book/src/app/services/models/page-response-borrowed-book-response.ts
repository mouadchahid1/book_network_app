/* tslint:disable */
/* eslint-disable */
import { BorrowedBookResponse } from '../models/borrowed-book-response';
export interface PageResponseBorrowedBookResponse {
  content?: Array<BorrowedBookResponse>;
  first?: boolean;
  last?: boolean;
  pageNumber?: number;
  size?: number;
  totalElement?: number;
  totalPage?: number;
}
