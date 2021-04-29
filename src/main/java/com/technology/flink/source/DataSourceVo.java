package com.technology.flink.source;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author
 */
public class DataSourceVo implements Serializable {

    private String sourceId;
    private byte[] bytesData;
    private Timestamp dateTimeStr;
    private String codecId;
    private String imageWidth;
    private String imageHeight;
    private String videoFrameRate;
    private byte[] extraData;
    private String flags;
    private String pts;
    private String dts;
    private String codecTimeBaseDen;
    private String streamTimeBaseDen;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public byte[] getBytesData() {
        return bytesData;
    }

    public void setBytesData(byte[] bytesData) {
        this.bytesData = bytesData;
    }

    public Timestamp getDateTimeStr() {
        return dateTimeStr;
    }

    public void setDateTimeStr(Timestamp dateTimeStr) {
        this.dateTimeStr = dateTimeStr;
    }

    public String getCodecId() {
        return codecId;
    }

    public void setCodecId(String codecId) {
        this.codecId = codecId;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getVideoFrameRate() {
        return videoFrameRate;
    }

    public void setVideoFrameRate(String videoFrameRate) {
        this.videoFrameRate = videoFrameRate;
    }

    public byte[] getExtraData() {
        return extraData;
    }

    public void setExtraData(byte[] extraData) {
        this.extraData = extraData;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    public String getPts() {
        return pts;
    }

    public void setPts(String pts) {
        this.pts = pts;
    }

    public String getDts() {
        return dts;
    }

    public void setDts(String dts) {
        this.dts = dts;
    }

    public String getCodecTimeBaseDen() {
        return codecTimeBaseDen;
    }

    public void setCodecTimeBaseDen(String codecTimeBaseDen) {
        this.codecTimeBaseDen = codecTimeBaseDen;
    }

    public String getStreamTimeBaseDen() {
        return streamTimeBaseDen;
    }

    public void setStreamTimeBaseDen(String streamTimeBaseDen) {
        this.streamTimeBaseDen = streamTimeBaseDen;
    }
}
