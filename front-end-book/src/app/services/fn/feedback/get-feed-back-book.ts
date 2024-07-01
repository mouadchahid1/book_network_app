/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PageResponseFeedBackResponse } from '../../models/page-response-feed-back-response';

export interface GetFeedBackBook$Params {
  page?: number;
  size?: number;
  book_id: number;
}

export function getFeedBackBook(http: HttpClient, rootUrl: string, params: GetFeedBackBook$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseFeedBackResponse>> {
  const rb = new RequestBuilder(rootUrl, getFeedBackBook.PATH, 'get');
  if (params) {
    rb.query('page', params.page, {});
    rb.query('size', params.size, {});
    rb.path('book_id', params.book_id, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<PageResponseFeedBackResponse>;
    })
  );
}

getFeedBackBook.PATH = '/feedback/feedBack/{book_id}';
