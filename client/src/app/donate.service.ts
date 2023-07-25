import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, catchError, throwError } from "rxjs";
import { DonationData } from "./models";

// const URLDonate = "http://localhost:8080/donate/thankyou"

const URLDonate = "https://olio.mandyykmt.com/donate/thankyou"

@Injectable()
export class DonateService {

    constructor(
        private http: HttpClient
    ) {}

    postDonation(donationData: DonationData): Observable<any> {
        return this.http.post<any>(URLDonate, donationData)
    }
}