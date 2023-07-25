import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { Listing } from "./models";

// const URLNewListing = "http://localhost:8080/listings/new"
// const URLGetAllListings = "http://localhost:8080/listings/all"
// const URLGetListingByKey = "http://localhost:8080/listings/search"
// const URLDeleteListing = "http://localhost:8080/listings/delete"
// const URLRequestListing = "http://localhost:8080/email/send"
// const URLGetListingByKeyword = "http://localhost:8080/listings/search/keyword"

const URLNewListing = "https://olio.mandyykmt.com/listings/new"
const URLGetAllListings = "https://olio.mandyykmt.com/listings/all"
const URLGetListingByKey = "https://olio.mandyykmt.com/listings/search"
const URLDeleteListing = "https://olio.mandyykmt.com/listings/delete"
const URLRequestListing = "https://olio.mandyykmt.com/email/send"
const URLGetListingByKeyword = "https://olio.mandyykmt.com/listings/search/keyword"

@Injectable()
export class ListingService {

    constructor(
        private http: HttpClient
    ) {}

    upload(listingName: string, description: string, email: string, file: File): Observable<any> {
        const formData = new FormData()
        formData.set('listingName', listingName)
        formData.set('description', description)
        formData.set('email', email)
        formData.set('file', file)

        return this.http.post<any>(URLNewListing, formData)
    }

    getListings(): Observable<Listing[] | null> {
        return this.http.get<Listing[]>(URLGetAllListings)
                .pipe(map((listings: Listing[]) => {
                    if (listings.length === 0) {
                        return null;
                    } else {
                        return listings;
                    }
                }))
    }

    getListingByKey(imageKey: string): Observable<Listing> {
        return this.http.get<Listing>(URLGetListingByKey + "/" + imageKey)
    }

    deleteListing(imageKey: string): Observable<any> {
        return this.http.delete<Listing>(URLDeleteListing + "/" + imageKey)
    }

    requestListing(ownerEmail: string, listingName: string, currentUserEmail: string): Observable<any> {
        const requestData = {
          recipientEmail: ownerEmail,
          requestorEmail: currentUserEmail,
          listingName: listingName
        };
    
        return this.http.post<any>(URLRequestListing, requestData);
    }

    getListingsByKeyword(keyword: string): Observable<Listing[]> {
        const params = new HttpParams().set('search', keyword)
        return this.http.get<Listing[]>(URLGetListingByKeyword, { params } )
    } 
}