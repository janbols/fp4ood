package be.guterfluss.account;


import be.guterfluss.transaction.TransactionUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.function.Function;

import static be.guterfluss.transaction.TransactionUtils.retrying;
import static be.guterfluss.transaction.TransactionUtils.withTransaction;

public class AccountController {

    private UserRepository userRepository;

    ServletResponse createAccount(ServletRequest req) {
        //...
        Account acc = constructAccount(req);
        AccountInsertResult r = userRepository.insert(acc);
        return respond(r);
    }


    ServletResponse createAccount(ServletRequest req,
                                  UserRepository userRepository) {
        //...
        Account acc = constructAccount(req);
        AccountInsertResult r = userRepository.insert(acc);
        return respond(r);
    }


    ServletResponse createAccount(ServletRequest req
            , Function<Account, AccountInsertResult> insertsAccount) {
        //…
        Account acc = constructAccount(req);
        AccountInsertResult r = insertsAccount.apply(acc);
        return respond(r);
    }


    private static ServletResponse respond(AccountInsertResult result) {
        return null;
    }

    private static Account constructAccount(ServletRequest req) {
        return new Account();
    }


    static class Utils {

        static ServletResponse createAccount(ServletRequest req
                , UserRepository userRepository) {
            //...
            Account acc = constructAccount(req);
            AccountInsertResult r = userRepository.insert(acc);
            return respond(r);
        }


        static ServletResponse createAccount(ServletRequest req
                , Function<Account, AccountInsertResult> insertsAccount
        ) {
            //…
            Account acc = constructAccount(req);
            AccountInsertResult r = insertsAccount.apply(acc);
            return respond(r);
        }

        static Function<ServletRequest, ServletResponse> createAccount(
                Function<Account, AccountInsertResult> insertsAccount
        ) {
            return req -> {
                //…
                Account acc = constructAccount(req);
                AccountInsertResult r = insertsAccount.apply(acc);
                return respond(r);
            };
        }


        static void client(UserRepository userDB, ServletRequest req) {
            Function<ServletRequest, ServletResponse> createsAccount =
                    createAccount(
                            (Account acc) -> userDB.insert(acc)
                    );
            Function<ServletRequest, ServletResponse> createsAccount2 =
                    createAccount(
                            (Account acc) -> withTransaction(
                                    () -> userDB.insert(acc)
                            )
                    );
            Function<ServletRequest, ServletResponse> createsAccount3 =
                    createAccount(
                            (Account acc) -> retrying(
                                    () -> withTransaction(
                                            () -> userDB.insert(acc)
                                    )
                            )
                    );

        }
    }
}
