package com.zaicev.spring.wallet.conversion;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import com.zaicev.spring.wallet.dao.WalletDAO;
import com.zaicev.spring.wallet.models.Wallet;

public class WalletConverter implements Formatter<Wallet> {

	private final WalletDAO walletDAO;

	public WalletConverter(WalletDAO walletDAO) {
		super();
		this.walletDAO = walletDAO;
	}

	@Override
	public Wallet parse(String text, Locale locale) throws ParseException {
		int walletId = Integer.valueOf(text);
		return walletDAO.getWalletById(walletId);
	}

	@Override
	public String print(Wallet object, Locale locale) {
		return (object != null ? Integer.toString(object.getId()) : "");
	}

}
