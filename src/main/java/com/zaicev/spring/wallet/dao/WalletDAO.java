package com.zaicev.spring.wallet.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.zaicev.spring.wallet.models.Wallet;

public class WalletDAO {

	private final JdbcTemplate jdbcTemplate;
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcInsert insertIntoUser;

	private final String SQL_SELECT_WALLET_BY_ID = "SELECT * FROM wallet WHERE wallet_id=?";
	private final String SQL_SELECT_WALLETS = "SELECT * FROM wallet";
	private final String SQL_SELECT_WALLETS_WITHOUT_CREATED = "SELECT * FROM wallet WHERE wallet_id NOT IN (:ids)";
	private final String SQL_UPDATE_WALLET = "UPDATE wallet SET wallet_name=?, wallet_balance=? WHERE wallet_id=?";
	private final String SQL_DELETE_WALLET = "DELETE FROM wallet WHERE wallet_id=?";

	private static Map<Integer, Wallet> walletPool = new HashMap<Integer, Wallet>();

	private final RowMapper<Wallet> walletMapper = (rs, rowNum) -> {
		Wallet wallet = new Wallet();
		wallet.setName(rs.getString("wallet_name"));
		wallet.setId(rs.getInt("wallet_id"));
		wallet.setBalance(rs.getBigDecimal("wallet_balance"));
		return wallet;
	};

	public WalletDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.jdbcTemplate = jdbcTemplate;
		this.insertIntoUser = new SimpleJdbcInsert(jdbcTemplate).withTableName("wallet")
				.usingGeneratedKeyColumns("wallet_id");
	}

	public List<Wallet> getAllWallets() {
		List<Wallet> wallets;
		if (walletPool.size() == 0) {
			wallets = jdbcTemplate.query(SQL_SELECT_WALLETS, walletMapper);
			wallets.forEach(x -> walletPool.put(x.getId(), x));
			return wallets;
		}

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", walletPool.keySet());

		wallets = namedParameterJdbcTemplate.query(SQL_SELECT_WALLETS_WITHOUT_CREATED, parameters, walletMapper);
		wallets.addAll(walletPool.values());
		return wallets;
	}

	public Wallet getWalletById(int id) {
		if (walletPool.containsKey(id)) {
			return walletPool.get(id);
		}

		Wallet wallet = jdbcTemplate.queryForObject(SQL_SELECT_WALLET_BY_ID, walletMapper, id);
		walletPool.put(id, wallet);
		return wallet;
	}

	public void createWallet(Wallet newWallet) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("wallet_name", newWallet.getName());
		parameters.addValue("wallet_balance", newWallet.getBalance());

		newWallet.setId(insertIntoUser.executeAndReturnKey(parameters).intValue());
		;
	}

	public void updateWallet(Wallet updatedWallet) {
		jdbcTemplate.update(SQL_UPDATE_WALLET, updatedWallet.getName(), updatedWallet.getBalance(), updatedWallet.getId());
		Wallet wallet = walletPool.get(updatedWallet.getId());
		wallet.setName(updatedWallet.getName());
		wallet.setBalance(updatedWallet.getBalance());
	}

	public void deleteWallet(int id) {
		jdbcTemplate.update(SQL_DELETE_WALLET, id);
		walletPool.remove(id);
	}
}
