/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.webkit;


import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;

public class WebViewClient {

    /**
     * Give the host application a chance to take over the control when a new
     * url is about to be loaded in the current WebView. If WebViewClient is not
     * provided, by default WebView will ask Activity Manager to choose the
     * proper handler for the url. If WebViewClient is provided, return true
     * means the host application handles the url, while return false means the
     * current WebView handles the url.
     * 
     * @param view The WebView that is initiating the callback.
     * @param url The url to be loaded.
     * @return True if the host application wants to leave the current WebView
     *         and handle the url itself, otherwise return false.
     */
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }

    /**
     * Notify the host application that a page has started loading. This method
     * is called once for each main frame load so a page with iframes or
     * framesets will call onPageStarted one time for the main frame. This also
     * means that onPageStarted will not be called when the contents of an
     * embedded frame changes, i.e. clicking a link whose target is an iframe.
     * 
     * @param view The WebView that is initiating the callback.
     * @param url The url to be loaded.
     * @param favicon The favicon for this page if it already exists in the
     *            database.
     */
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
    }

    /**
     * Notify the host application that a page has finished loading. This method
     * is called only for main frame. When onPageFinished() is called, the
     * rendering picture may not be updated yet. To get the notification for the
     * new Picture, use {@link WebView.PictureListener#onNewPicture}.
     * 
     * @param view The WebView that is initiating the callback.
     * @param url The url of the page.
     */
    public void onPageFinished(WebView view, String url) {
    }

    /**
     * Notify the host application that the WebView will load the resource
     * specified by the given url.
     * 
     * @param view The WebView that is initiating the callback.
     * @param url The url of the resource the WebView will load.
     */
    public void onLoadResource(WebView view, String url) {
    }

    /**
     * Notify the host application that there have been an excessive number of
     * HTTP redirects. As the host application if it would like to continue
     * trying to load the resource. The default behavior is to send the cancel
     * message.
     * 
     * @param view The WebView that is initiating the callback.
     * @param cancelMsg The message to send if the host wants to cancel
     * @param continueMsg The message to send if the host wants to continue
     */
    public void onTooManyRedirects(WebView view, Message cancelMsg,
            Message continueMsg) {
        cancelMsg.sendToTarget();
    }

    /**
     * Report an error to an activity. These errors come up from WebCore, and
     * are network errors.
     * 
     * @param view The WebView that is initiating the callback.
     * @param errorCode The HTTP error code.
     * @param description A String description.
     * @param failingUrl The url that failed.
     */
    public void onReceivedError(WebView view, int errorCode,
            String description, String failingUrl) {
    }

    /**
     * As the host application if the browser should resend data as the
     * requested page was a result of a POST. The default is to not resend the
     * data.
     * 
     * @param view The WebView that is initiating the callback.
     * @param dontResend The message to send if the browser should not resend
     * @param resend The message to send if the browser should resend data
     */
    public void onFormResubmission(WebView view, Message dontResend,
            Message resend) {
        dontResend.sendToTarget();
    }

    /**
     * Notify the host application to update its visited links database.
     * 
     * @param view The WebView that is initiating the callback.
     * @param url The url being visited.
     * @param isReload True if this url is being reloaded.
     */
    public void doUpdateVisitedHistory(WebView view, String url,
            boolean isReload) {
    }

    /**
     * Notify the host application to handle a ssl certificate error request
     * (display the error to the user and ask whether to proceed or not). The
     * host application has to call either handler.cancel() or handler.proceed()
     * as the connection is suspended and waiting for the response. The default
     * behavior is to cancel the load.
     * 
     * @param view The WebView that is initiating the callback.
     * @param handler An SslErrorHandler object that will handle the user's
     *            response.
     * @param error The SSL error object.
     * @hide - hide this because it contains a parameter of type SslError,
     * which is located in a hidden package.
     */
    public void onReceivedSslError(WebView view, SslErrorHandler handler,
            SslError error) {
        handler.cancel();
    }

    /**
     * Notify the host application to handle an authentication request. The
     * default behavior is to cancel the request.
     * 
     * @param view The WebView that is initiating the callback.
     * @param handler The HttpAuthHandler that will handle the user's response.
     * @param host The host requiring authentication.
     * @param realm A description to help store user credentials for future
     *            visits.
     */
    public void onReceivedHttpAuthRequest(WebView view,
            HttpAuthHandler handler, String host, String realm) {
        handler.cancel();
    }

    /**
     * Give the host application a chance to handle the key event synchronously.
     * e.g. menu shortcut key events need to be filtered this way. If return
     * true, WebView will not handle the key event. If return false, WebView
     * will always handle the key event, so none of the super in the view chain
     * will see the key event. The default behavior returns false.
     * 
     * @param view The WebView that is initiating the callback.
     * @param event The key event.
     * @return True if the host application wants to handle the key event
     *         itself, otherwise return false
     */
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        return false;
    }

    /**
     * Notify the host application that a key was not handled by the WebView.
     * Except system keys, WebView always consumes the keys in the normal flow
     * or if shouldOverrideKeyEvent returns true. This is called asynchronously
     * from where the key is dispatched. It gives the host application an chance
     * to handle the unhandled key events.
     * 
     * @param view The WebView that is initiating the callback.
     * @param event The key event.
     */
    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
    }

    /**
     * Notify the host application that the scale applied to the WebView has
     * changed.
     * 
     * @param view he WebView that is initiating the callback.
     * @param oldScale The old scale factor
     * @param newScale The new scale factor
     */
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
    }
}

