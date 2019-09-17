/**
 * Copyright 2017 Bo Song
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bosong.commonitemdecoration;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.View;

/**
 * Set dividers' properties(horizontal and vertical space...) of item with type.
 * 通过item type 设置边框属性
 * Created by bosong on 2017/3/10.
 */

public class SCommonItemDecoration extends RecyclerView.ItemDecoration {

    public static ItemDecorationBuilder builder() {
        return new ItemDecorationBuilder();
    }

    private SparseArray<ItemDecorationProps> mPropMap; // itemType -> prop

    SCommonItemDecoration(SparseArray<ItemDecorationProps> propMap) {
        mPropMap = propMap;
    }

    SparseArray<ItemDecorationProps> getPropMap() {
        return mPropMap;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter == null) {
            return;
        }
        int itemType = adapter.getItemViewType(position);

        ItemDecorationProps props;
        if (mPropMap != null) {
            props = mPropMap.get(itemType);
        } else {
            return;
        }
        if (props == null) {
            return;
        }
        int spanIndex = 0;
        int spanSize = 1;
        int spanCount = 1;
        int orientation = RecyclerView.VERTICAL;
        if(parent.getLayoutManager() instanceof GridLayoutManager){
            GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
            spanIndex = lp.getSpanIndex();
            spanSize = lp.getSpanSize();
            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            spanCount = layoutManager.getSpanCount(); // Assume that there're spanCount items in this row/column.
            orientation = layoutManager.getOrientation();
        }else if(parent.getLayoutManager() instanceof StaggeredGridLayoutManager){
            StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            spanIndex = lp.getSpanIndex();
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) parent.getLayoutManager();
            spanCount = layoutManager.getSpanCount(); // Assume that there're spanCount items in this row/column.
            spanSize = lp.isFullSpan() ? spanCount : 1;
            orientation = layoutManager.getOrientation();
        }

        boolean isFirstRowOrColumn, isLastRowOrColumn;
        int prePos = position > 0 ? position - 1 : -1;
        int nextPos = position < adapter.getItemCount() - 1 ? position + 1 : -1;
        // Last position on the last row 上一行的最后一个位置
        int preRowPos = position > spanIndex ? position - (1 + spanIndex) : -1;
        // First position on the next row 下一行的第一个位置
        int nextRowPos = position < adapter.getItemCount() - (spanCount - spanIndex) ? position + (spanCount - spanIndex) - spanSize + 1 : -1;
        isFirstRowOrColumn = position == 0 || prePos == -1 || itemType != adapter.getItemViewType(prePos) ||
                preRowPos == -1 || itemType != adapter.getItemViewType(preRowPos);
        isLastRowOrColumn = position == adapter.getItemCount() - 1 || nextPos == -1 || itemType != adapter.getItemViewType(nextPos) ||
                nextRowPos == -1 || itemType != adapter.getItemViewType(nextRowPos);

        int left = 0, top = 0, right = 0, bottom = 0;

        if (orientation == GridLayoutManager.VERTICAL) {

            if (props.getHasLeftRightEdge()) {
                left = props.getLeftRightSpace() * (spanCount - spanIndex) / spanCount;
                right = props.getLeftRightSpace() * (spanIndex + (spanSize - 1) + 1) / spanCount;
            } else {
                left = props.getLeftRightSpace() * spanIndex / spanCount;
                right = props.getLeftRightSpace() * (spanCount - (spanIndex + spanSize - 1) - 1) / spanCount;
            }

            if (isFirstRowOrColumn) { // First row
                if (props.getHasTopBottomEdge()) {
                    top = props.getTopBottomSpace();
                }
            }
            if (isLastRowOrColumn) { // Last row
                if (props.getHasTopBottomEdge()) {
                    bottom = props.getTopBottomSpace();
                }
            } else {
                bottom = props.getTopBottomSpace();
            }
        } else {

            if (props.getHasTopBottomEdge()) {
                top = props.getTopBottomSpace() * (spanCount - spanIndex) / spanCount;
                bottom = props.getTopBottomSpace() * (spanIndex + (spanSize - 1) + 1) / spanCount;
            } else {
                top = props.getTopBottomSpace() * spanIndex / spanCount;
                bottom = props.getTopBottomSpace() * (spanCount - (spanIndex + spanSize - 1) - 1) / spanCount;
            }

            if (isFirstRowOrColumn) { // First column
                if (props.getHasLeftRightEdge()) {
                    left = props.getLeftRightSpace();
                }
            }
            if (isLastRowOrColumn) { // Last column
                if (props.getHasLeftRightEdge()) {
                    right = props.getLeftRightSpace();
                }
            } else {
                right = props.getLeftRightSpace();
            }
        }

        outRect.set(left, top, right, bottom);
    }

    public static class ItemDecorationProps {
        /**
         * Space in left or right between items
         * 左右间距
         */
        private int mLeftRightSpace;
        /**
         * Space in top or bottom between items
         * 上下间距
         */
        private int mTopBottomSpace;
        /**
         * If this type of items has left and right space
         * 是否包含左右边距
         */
        private boolean mHasLeftRightEdge;
        /**
         * If this type of items has top and bottom space
         * 是否包含上下边距
         */
        private boolean mHasTopBottomEdge;

        ItemDecorationProps() {

        }

        public ItemDecorationProps(int topBottomSpace, int leftRightSpace, boolean hasTopBottomEdge, boolean hasLeftRightEdge) {
            this.mLeftRightSpace = leftRightSpace;
            this.mTopBottomSpace = topBottomSpace;
            this.mHasTopBottomEdge = hasTopBottomEdge;
            this.mHasLeftRightEdge = hasLeftRightEdge;
        }

        int getTopBottomSpace() {
            return this.mTopBottomSpace;
        }

        int getLeftRightSpace() {
            return this.mLeftRightSpace;
        }

        boolean getHasTopBottomEdge() {
            return this.mHasTopBottomEdge;
        }

        boolean getHasLeftRightEdge() {
            return this.mHasLeftRightEdge;
        }

        void setLeftRightSpace(int leftRightSpace) {
            mLeftRightSpace = leftRightSpace;
        }

        void setTopBottomSpace(int topBottomSpace) {
            mTopBottomSpace = topBottomSpace;
        }

        void setHasLeftRightEdge(boolean hasLeftRightEdge) {
            mHasLeftRightEdge = hasLeftRightEdge;
        }

        void setHasTopBottomEdge(boolean hasTopBottomEdge) {
            mHasTopBottomEdge = hasTopBottomEdge;
        }
    }

    public static class ItemDecorationBuilder {
        private SparseArray<ItemDecorationProps> mPropMap = new SparseArray<>(); // itemType -> prop

        SparseArray<ItemDecorationProps> getPropMap() {
            return mPropMap;
        }

        public ItemType type(int type) {
            return new ItemType(type, this);
        }

        public SCommonItemDecoration build() {
            return new SCommonItemDecoration(mPropMap);
        }
    }

    public static class ItemType {
        private int mType;
        private ItemDecorationProps mProps = new ItemDecorationProps();

        private ItemDecorationBuilder mBuilder;

        ItemType(int type, ItemDecorationBuilder builder) {
            mType = type;
            mBuilder = builder;
        }

        public ItemType prop(int leftRightSpace, int topBottomSpace, boolean hasLeftRightEdge, boolean hasTopBottomEdge) {
            mProps.setLeftRightSpace(leftRightSpace);
            mProps.setTopBottomSpace(topBottomSpace);
            mProps.setHasLeftRightEdge(hasLeftRightEdge);
            mProps.setHasTopBottomEdge(hasTopBottomEdge);
            return this;
        }

        public ItemType leftRightSpace(int space) {
            mProps.setLeftRightSpace(space);
            return this;
        }

        public ItemType topBottomSpace(int space) {
            mProps.setTopBottomSpace(space);
            return this;
        }

        public ItemType hasLeftRightEdge(boolean hasEdge) {
            mProps.setHasLeftRightEdge(hasEdge);
            return this;
        }

        public ItemType hasTopBottomEdge(boolean hasEdge) {
            mProps.setHasTopBottomEdge(hasEdge);
            return this;
        }

        public ItemDecorationBuilder buildType() {
            mBuilder.getPropMap().put(mType, mProps);
            return mBuilder;
        }
    }
}
