package com.epam.musicbox.entity;

public class Track implements Entity {

    private Long id;
    private String name;
    private String audio;
    private Long albumId;

    public Track() {
    }

    public Track(Long id, String name, String audio, Long albumId) {
        this.id = id;
        this.name = name;
        this.albumId = albumId;
        this.audio = audio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        if (!(id == null ? track.id == null : id.equals(track.id)))
            return false;
        if (!(name == null ? track.name == null : name.equals(track.name)))
            return false;
        if (!(audio == null ? track.audio == null : audio.equals(track.audio)))
            return false;
        if (!(albumId == null ? track.albumId == null : albumId.equals(track.albumId)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + (id == null ? 0 : id.hashCode());
        hash = hash * 31 + (name == null ? 0 : name.hashCode());
        hash = hash * 31 + (audio == null ? 0 : audio.hashCode());
        hash = hash * 31 + (albumId == null ? 0 : albumId.hashCode());
        return hash;
    }

    @Override
    public String toString() {
        return new StringBuilder("Track{")
                .append("id=").append(id)
                .append(", name='").append(name).append('\'')
                .append(", audio='").append(audio).append('\'')
                .append(", albumId=").append(albumId)
                .append('}')
                .toString();
    }
}
