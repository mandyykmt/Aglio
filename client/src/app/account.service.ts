import { HttpClient, HttpResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { User, EmailLogin } from "./models";
import { BehaviorSubject, Observable, map } from "rxjs";

const URLRegister = "http://localhost:8080/account/register"
const URLLogin = "http://localhost:8080/account/login"

// const URLRegister = "https://olio.mandyykmt.com/account/register"
// const URLLogin = "https://olio.mandyykmt.com/account/login"

@Injectable()
export class AccountService {

    private localStorage$: BehaviorSubject<string | null> 
        = new BehaviorSubject<string | null>(localStorage.getItem('user'))

    constructor(
        private http: HttpClient,
    ) {
        window.addEventListener('storage', this.onStorageChange);
    }
    
    getLocalStorage$(): Observable<string | null> {
        return this.localStorage$.asObservable();
    }

    register(user: User) : Observable<any> {
        return this.http.post(URLRegister, user)
    }

    login(email: string, password: string) {
        return this.http.post<EmailLogin>(URLLogin, {email, password}, {observe: 'response'})
                        .pipe(map(response => {
                            if (response.status === 404 || response.status === 401) {
                                localStorage.setItem('user', JSON.stringify(null));
                                this.localStorage$.next(null)
                            } 
                            let user = response.body
                            localStorage.setItem('user', JSON.stringify(user));
                            this.localStorage$.next(JSON.stringify(user));
                            return user;
                        }))
    }

    private onStorageChange = (event: any) => {
        if (event.key === "user") {
            const currentValue = event.newValue;
            this.localStorage$.next(currentValue);
        }
    };

    isLoggedIn(): boolean {
        return !!this.localStorage$.getValue();
    }
    
    logout(): void {
        localStorage.removeItem('user')
        this.localStorage$.next(null)
    }
} 