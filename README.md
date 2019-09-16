# A custom ItemDecoration which appends dividers(with same thickness) between items(with same dimension) for RecyclerView

[中文版](README_zh.md)

When using RecyclerView, we often need set dividers between items. Usually, we set right_margin to the root layout of each item. In this way, you hava to set a left_margin to the RecyclerView to make the list looks symmetric. There's another way to do this. We can customize ItemDecoration and override the getItemOffsets method. Also, set `right` to the parameter `outRect` to each item. And add `left` to the first item. In this way, you will find that the width between the first and other items are not the same.

So this `SCommonItemDecoration` is the best way. It supports LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager, also supports different orientation.

### Usage

``` java
mRecyclerView.addItemDecoration(
                SCommonItemDecoration.builder()
                        .type(TYPE_1)
                        .prop(verticalSpace, horizaontalSpace, hasVerticalEdge, hasHorizontalEdge)
                        .buildType()
                        .type(TYPE_2)
                        .prop(verticalSpace, horizaontalSpace, hasVerticalEdge, hasHorizontalEdge)
                        .buildType()
                        .build()
        );
```

That's all, enjoy youself.

![](demo.gif)
