/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { getFeedBackBook } from '../fn/feedback/get-feed-back-book';
import { GetFeedBackBook$Params } from '../fn/feedback/get-feed-back-book';
import { PageResponseFeedBackResponse } from '../models/page-response-feed-back-response';
import { saveFeedBack } from '../fn/feedback/save-feed-back';
import { SaveFeedBack$Params } from '../fn/feedback/save-feed-back';


/**
 * The controller of the feedBack
 */
@Injectable({ providedIn: 'root' })
export class FeedbackService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `saveFeedBack()` */
  static readonly SaveFeedBackPath = '/feedback/';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `saveFeedBack()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  saveFeedBack$Response(params: SaveFeedBack$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return saveFeedBack(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `saveFeedBack$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  saveFeedBack(params: SaveFeedBack$Params, context?: HttpContext): Observable<number> {
    return this.saveFeedBack$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `getFeedBackBook()` */
  static readonly GetFeedBackBookPath = '/feedback/feedBack/{book_id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getFeedBackBook()` instead.
   *
   * This method doesn't expect any request body.
   */
  getFeedBackBook$Response(params: GetFeedBackBook$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseFeedBackResponse>> {
    return getFeedBackBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getFeedBackBook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getFeedBackBook(params: GetFeedBackBook$Params, context?: HttpContext): Observable<PageResponseFeedBackResponse> {
    return this.getFeedBackBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseFeedBackResponse>): PageResponseFeedBackResponse => r.body)
    );
  }

}
