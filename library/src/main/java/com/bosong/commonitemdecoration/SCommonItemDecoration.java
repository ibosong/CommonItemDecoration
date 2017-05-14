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
    private SparseArray<ItemDecorationProps> mPropMap; // itemType -> prop

    public SCommonItemDecoration(SparseArray<ItemDecorationProps> propMap) {
        mPropMap = propMap;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        RecyclerView.Adapter adapter = parent.getAdapter();
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
        int orientation = OrientationHelper.VERTICAL;
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
        int nextRowPos = position < adapter.getItemCount() - (spanCount - spanIndex) ? position + (spanCount - spanIndex) : -1;
        isFirstRowOrColumn = position == 0 || prePos == -1 || itemType != adapter.getItemViewType(prePos) ||
                preRowPos == -1 || itemType != adapter.getItemViewType(preRowPos);
        isLastRowOrColumn = position == adapter.getItemCount() - 1 || nextPos == -1 || itemType != adapter.getItemViewType(nextPos) ||
                nextRowPos == -1 || itemType != adapter.getItemViewType(nextRowPos);

        int left = 0, top = 0, right = 0, bottom = 0;

        if (orientation == GridLayoutManager.VERTICAL) {

            if (props.getHasVerticalEdge()) {
                left = props.getVerticalSpace() * (spanCount - spanIndex) / spanCount;
                right = props.getVerticalSpace() * (spanIndex + (spanSize - 1) + 1) / spanCount;
            } else {
                left = props.getVerticalSpace() * spanIndex / spanCount;
                right = props.getVerticalSpace() * (spanCount - (spanIndex + spanSize - 1) - 1) / spanCount;
            }

            if (isFirstRowOrColumn) { // First row
                if (props.getHasHorizontalEdge()) {
                    top = props.getHorizontalSpace();
                }
            }
            if (isLastRowOrColumn) { // Last row
                if (props.getHasHorizontalEdge()) {
                    bottom = props.getHorizontalSpace();
                }
            } else {
                bottom = props.getHorizontalSpace();
            }
        } else {

            if (props.getHasHorizontalEdge()) {
                top = props.getHorizontalSpace() * (spanCount - spanIndex) / spanCount;
                bottom = props.getHorizontalSpace() * (spanIndex + (spanSize - 1) + 1) / spanCount;
            } else {
                top = props.getHorizontalSpace() * spanIndex / spanCount;
                bottom = props.getHorizontalSpace() * (spanCount - (spanIndex + spanSize - 1) - 1) / spanCount;
            }

            if (isFirstRowOrColumn) { // First column
                if (props.getHasVerticalEdge()) {
                    left = props.getVerticalSpace();
                }
            }
            if (isLastRowOrColumn) { // Last column
                if (props.getHasVerticalEdge()) {
                    right = props.getVerticalSpace();
                }
            } else {
                right = props.getVerticalSpace();
            }
        }

        outRect.set(left, top, right, bottom);
    }

    public static class ItemDecorationProps {
        private int verticalSpace;
        private int horizontalSpace;
        private boolean hasVerticalEdge;
        private boolean hasHorizontalEdge;

        public ItemDecorationProps(int horizontalSpace, int verticalSpace, boolean hasHorizontalEdge, boolean hasVerticalEdge) {
            this.verticalSpace = verticalSpace;
            this.horizontalSpace = horizontalSpace;
            this.hasHorizontalEdge = hasHorizontalEdge;
            this.hasVerticalEdge = hasVerticalEdge;
        }

        public int getHorizontalSpace() {
            return this.horizontalSpace;
        }

        public int getVerticalSpace() {
            return this.verticalSpace;
        }

        public boolean getHasHorizontalEdge() {
            return this.hasHorizontalEdge;
        }

        public boolean getHasVerticalEdge() {
            return this.hasVerticalEdge;
        }
    }
}
