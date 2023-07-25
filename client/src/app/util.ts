import { CanActivateFn, Router } from "@angular/router";
import { ListingService } from "./listing.service";
import { AccountService } from "./account.service";
import { inject } from "@angular/core";

export interface Util {
    
}

export const loginGuard: CanActivateFn = (route, state) => {
    
    const accountService = inject(AccountService)
    const listingService = inject(ListingService)
    const router = inject(Router)

    if (accountService.isLoggedIn()) {
        return true
    } else {
        return router.createUrlTree(['/']); 
    }
}