package com.thinkhodl.shemareminder.backend;

public class Prayer {

    /*
     * Different default variables
     */
    public static final int TYPE_TITLE = 0;
    public static final int TYPE_MAIN_PRAYER = 1;
    public static final int TYPE_SUBTITLE = 2;
    public static final int TYPE_CAPTION = 3;


    /*
     *  int to the string resource of the prayer
     */
    private int mContentResource;
    private int mCategoryResource;
    private int mCategoryOrder;
    private int mType;

    public Prayer(int mContentResource, int mCategoryResource, int mCategoryOrder, int mType) {
        this.mContentResource = mContentResource;
        this.mCategoryResource = mCategoryResource;
        this.mCategoryOrder = mCategoryOrder;
        this.mType = mType;
    }

    public Prayer() {
        this.mContentResource = -1;
        this.mCategoryResource = -1;
        this.mCategoryOrder = -1;
        this.mType = -1;
    }

    public Prayer(int mContentResource) {
        this.mContentResource = mContentResource;
        this.mCategoryResource = -1;
        this.mCategoryOrder = -1;
        this.mType = TYPE_MAIN_PRAYER;
    }

    public Prayer(int mContentResource, int mType) {
        this.mContentResource = mContentResource;
        this.mType = mType;
    }

    public int getType() {
        return mType;
    }

    public void setType(int mType) {
        this.mType = mType;
    }


    public int getContentResource() {
        return mContentResource;
    }

    public void setContentResource(int mContentRessource) {
        this.mContentResource = mContentRessource;
    }

    public int getCategoryResource() {
        return mCategoryResource;
    }

    public void setCategoryResource(int mCategoryResource) {
        this.mCategoryResource = mCategoryResource;
    }

    public int getmCategoryOrder() {
        return mCategoryOrder;
    }

    public void setmCategoryOrder(int mCategoryOrder) {
        this.mCategoryOrder = mCategoryOrder;
    }

}
