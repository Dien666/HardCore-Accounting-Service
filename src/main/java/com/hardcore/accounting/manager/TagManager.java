package com.hardcore.accounting.manager;

import com.github.pagehelper.PageInfo;
import com.hardcore.accounting.model.common.Tag;

public interface TagManager {
    Tag createTag(String description, Long userId);

    Tag getTagByTagId(Long tagId);

    Tag updateTag(Tag tag);

    PageInfo<Tag> getTags(Long userId, int pageNum, int pageSize);
}
