package com.techcavern.wavetact.utils;

import org.jooq.Record;
import org.jooq.Result;

import static com.techcavern.wavetactdb.Tables.*;

/**
 * Created by jztech101 on 12/30/14.
 */
public class databaseUtils {
    public static void createAccount(String account, String password){
        Registry.WaveTactDB.insertInto(ACCOUNTS).values(account, password).execute();
    }

    public static Record getAccount(String account) {
        Result<Record> accountRecord =  Registry.WaveTactDB.select().from(ACCOUNTS).where(ACCOUNTS.USERNAME.eq(account)).fetch();
        return getRecord(accountRecord);
    }
    public static void removeAccount(String account) {
        Registry.WaveTactDB.delete(ACCOUNTS).where(ACCOUNTS.USERNAME.eq(account)).execute();
    }
    public static void createBan(String network, String channel, String hostmask, long init, long time, boolean isMute, String property){
        Registry.WaveTactDB.insertInto(BANS).values(hostmask, network,channel,init, time,isMute, property).execute();
    }
    public static void removeBan(String network, String channel, String hostmask, boolean isMute){
        Registry.WaveTactDB.delete(BANS).where(BANS.HOSTMASK.eq(hostmask).and(BANS.NETWORK.eq(network)).and(BANS.ISMUTE.eq(isMute).and(BANS.CHANNEL.eq(channel)))).execute();
    }
    public static Record getBan(String network, String channel, String hostmask, boolean isMute){
        Result<Record> banRecord = Registry.WaveTactDB.select().from(BANS).where(BANS.HOSTMASK.eq(hostmask)).and(BANS.NETWORK.eq(network)).and(BANS.ISMUTE.eq(isMute).and(BANS.CHANNEL.eq(channel))).fetch();
        return getRecord(banRecord);
    }
    public static Result<Record> getBans(){
        return Registry.WaveTactDB.select().from(BANS).orderBy(BANS.HOSTMASK.asc()).fetch();
    }
    public static Record getConfig(String config){
        Result<Record> configRecord = Registry.WaveTactDB.select().from(CONFIG).where(CONFIG.PROPERTY.eq(config)).fetch();
        return getRecord(configRecord);
    }

    public static void removeConfig(String config){
        Registry.WaveTactDB.delete(CONFIG).where(CONFIG.PROPERTY.eq(config)).execute();
    }

    public static void addConfig(String config, String value){
        Registry.WaveTactDB.insertInto(CONFIG).values(config, value).execute();
    }

    public static Record getRecord(Result<Record> record){
        if(record.size() >0){
            return record.get(0);
        }else{
            return null;
        }

    }
    public static Record getPermUserChannel(String network, String channel, String account){
        Result<Record> permUserChannel = Registry.WaveTactDB.select().from(PERMUSERCHANNELS).where(PERMUSERCHANNELS.ACCOUNT.eq(account)).and(PERMUSERCHANNELS.CHANNEL.eq(channel)).and(PERMUSERCHANNELS.NETWORK.eq(network)).fetch();
        return getRecord(permUserChannel);
    }
    public static void addPermUserChannel(String network, String channel, String account, String permlevel){
        Registry.WaveTactDB.insertInto(PERMUSERCHANNELS).values(network, channel, account, permlevel).execute();
    }
    public static void deletePermUserChannel(String network, String channel, String account){
        Registry.WaveTactDB.delete(PERMUSERCHANNELS).where(PERMUSERCHANNELS.ACCOUNT.eq(account)).and(PERMUSERCHANNELS.CHANNEL.eq(channel)).and(PERMUSERCHANNELS.NETWORK.eq(network)).execute();
    }
    public static Record getCustomCommand(String channel,String network, String command){
        Result<Record> commandRecord = Registry.WaveTactDB.select().from(CUSTOMCOMMANDS).where(CUSTOMCOMMANDS.COMMAND.eq(command)).and(CUSTOMCOMMANDS.NETWORK.eq(network)).and(CUSTOMCOMMANDS.CHANNEL.eq(channel)).fetch();
        if(getRecord(commandRecord) == null){
            commandRecord = Registry.WaveTactDB.select().from(CUSTOMCOMMANDS).where(CUSTOMCOMMANDS.COMMAND.eq(command)).and(CUSTOMCOMMANDS.NETWORK.eq(network).and(CUSTOMCOMMANDS.CHANNEL.eq((String) null))).fetch();
        }
        return getRecord(commandRecord);

    }
    public static void addCustomCommand(String channel, String command, int permlevel, String value, String network, boolean isLocked, boolean isAction){
        Registry.WaveTactDB.insertInto(CUSTOMCOMMANDS).values(permlevel,channel,command, value, network, isLocked, isAction).execute();
    }
    public static void deleteCustomCommand(String channel, String network, String command){
        Registry.WaveTactDB.delete(CUSTOMCOMMANDS).where(CUSTOMCOMMANDS.COMMAND.eq(command)).and(CUSTOMCOMMANDS.NETWORK.eq(network)).and(CUSTOMCOMMANDS.CHANNEL.eq(channel)).execute();
    }
    public static Result<Record> getBlacklists(String type){
        return Registry.WaveTactDB.select().from(BLACKLISTS).where(BLACKLISTS.TYPE.eq(type)).fetch();
    }

    public static void removeBlacklist(String type, String blacklist){
        Registry.WaveTactDB.delete(BLACKLISTS).where(BLACKLISTS.TYPE.eq(type)).and(BLACKLISTS.URL.eq(blacklist)).execute();
    }

    public static void addBlacklist(String blacklist, String type){
        Registry.WaveTactDB.insertInto(BLACKLISTS).values(type, blacklist).execute();
    }
    public static Record getChannelProperty(String channel,String network, String property){
        Result<Record> commandRecord = Registry.WaveTactDB.select().from(CHANNELPROPERTY).where(CHANNELPROPERTY.PROPERTY.eq(property)).and(CHANNELPROPERTY.NETWORK.eq(network)).and(CHANNELPROPERTY.CHANNEL.eq(channel)).fetch();
        return getRecord(commandRecord);

    }
    public static void addChannelProperty(String channel, String value, String property, String network){
        Registry.WaveTactDB.insertInto(CHANNELPROPERTY).values(network, channel, property, value).execute();
    }
    public static void deleteChannelProperty(String channel, String network, String property){
        Registry.WaveTactDB.delete(CHANNELPROPERTY).where(CHANNELPROPERTY.PROPERTY.eq(property)).and(CHANNELPROPERTY.NETWORK.eq(network)).and(CHANNELPROPERTY.CHANNEL.eq(channel)).execute();
    }
    public static Result<Record> getServers(){
        return Registry.WaveTactDB.select().from(SERVERS).fetch();
    }
    public static Record getServer(String name){
        Result<Record> serverRecord = Registry.WaveTactDB.select().from(SERVERS).where(SERVERS.NAME.eq(name)).fetch();
        return getRecord(serverRecord);
    }
    public static void deleteServer(String name){
        Registry.WaveTactDB.delete(SERVERS).where(SERVERS.NAME.eq(name));
    }
    public static void addServer(String name, int port, String server, String nick, String channels, String nickserv, String bindhost, boolean netadminaccess, String authtype, String controllers){
        Registry.WaveTactDB.insertInto(SERVERS).values(name, port,server, nick, channels, nickserv, bindhost, netadminaccess, authtype, controllers);
    }


}