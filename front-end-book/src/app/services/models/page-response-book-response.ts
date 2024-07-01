/* tslint:disable */
/* eslint-disable */
import { BookResponse } from '../models/book-response';
export interface PageResponseBookResponse {
  content?: Array<BookResponse>;
  first?: boolean;
  last?: boolean;
  pageNumber?: number;
  size?: number;
  totalElement?: number;
  totalPage?: number;
}
