package com.epam.musicbox.controller.filter.access;

import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.entity.Role;

import java.util.*;

/**
 * The type Role rights.
 */
public class RoleRights {

    private static final RoleRights instance = new RoleRights();

    private final Map<Role, Set<CommandType>> map;

    private RoleRights() {
        Map<Role, Set<CommandType>> map = new EnumMap<>(Role.class);

        map.put(Role.GUEST, setOf(
                CommandType.LOGIN_PAGE,
                CommandType.SIGN_UP_PAGE,
                CommandType.HOME_PAGE,

                CommandType.CHANGE_LOCALE,

                CommandType.SIGN_UP,
                CommandType.LOGIN,
                CommandType.LOGOUT));

        map.put(Role.USER, setOf(
                CommandType.HOME_PAGE,
                CommandType.PROFILE_PAGE,

                CommandType.CHANGE_PASSWORD_PAGE,

                CommandType.EDIT_PLAYLIST_PAGE,

                CommandType.LOGOUT,
                CommandType.CHANGE_PASSWORD,

                CommandType.CHANGE_LOCALE,
                CommandType.SEARCH,

                CommandType.TRACK_GET,
                CommandType.TRACK_GET_BY_ID,
                CommandType.TRACK_GET_BY_NAME,

                CommandType.ALBUM_GET,
                CommandType.ALBUM_GET_BY_ID,
                CommandType.ALBUM_GET_BY_NAME,

                CommandType.ARTIST_GET,
                CommandType.ARTIST_GET_BY_ID,
                CommandType.ARTIST_GET_BY_NAME,

                CommandType.PLAYLIST_SAVE,
                CommandType.PLAYLIST_DELETE,
                CommandType.PLAYLIST_GET,
                CommandType.PLAYLIST_GET_BY_ID,
                CommandType.PLAYLIST_GET_BY_NAME,
                CommandType.PLAYLIST_ADD_TRACK,
                CommandType.PLAYLIST_REMOVE_TRACK,

                CommandType.USER_GET_LIKED_ALBUMS,
                CommandType.USER_MARK_LIKED_ALBUM,
                CommandType.USER_UNMARK_LIKED_ALBUM,
                CommandType.USER_GET_LIKED_ARTISTS,
                CommandType.USER_MARK_LIKED_ARTIST,
                CommandType.USER_UNMARK_LIKED_ARTIST,
                CommandType.USER_GET_LIKED_TRACKS,
                CommandType.USER_MARK_LIKED_TRACK,
                CommandType.USER_UNMARK_LIKED_TRACK,
                CommandType.USER_GET_PLAYLISTS));

        map.put(Role.ADMIN, EnumSet.allOf(CommandType.class));

        this.map = map;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static RoleRights getInstance() {
        return instance;
    }

    private Set<CommandType> setOf(CommandType... commandTypes) {
        return EnumSet.copyOf(Arrays.asList(commandTypes));
    }

    /**
     * Is exist command type for role.
     *
     * @param role the user role
     * @param type the command type
     * @return the boolean
     */
    public boolean isExistCommandType(Role role, CommandType type) {
        Set<CommandType> commands = map.get(role);
        return commands != null && commands.contains(type);
    }
}