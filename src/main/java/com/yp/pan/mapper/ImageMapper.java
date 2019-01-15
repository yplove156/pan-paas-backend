package com.yp.pan.mapper;

import com.yp.pan.dto.ImageDto;
import com.yp.pan.model.ImageInfo;
import com.yp.pan.provider.ImageProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * ImageMapper class
 *
 * @author Administrator
 * @date 2018/12/13
 */
public interface ImageMapper {

    @InsertProvider(type = ImageProvider.class, method = "addImage")
    int addImage(ImageInfo imageInfo);

    @SelectProvider(type = ImageProvider.class, method = "publicImages")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "create_time", property = "createTime")
    })
    List<ImageDto> publicImages(Map<String, Object> params);

    @SelectProvider(type = ImageProvider.class, method = "publicImageNo")
    int publicImageNo();

    @SelectProvider(type = ImageProvider.class, method = "privateImageNo")
    int privateImageNo(String userId);

    @SelectProvider(type = ImageProvider.class, method = "privateImages")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "create_time", property = "createTime")
    })
    List<ImageDto> privateImages(Map<String, Object> params);

    @UpdateProvider(type = ImageProvider.class, method = "delete")
    int delete(String id);

    @UpdateProvider(type = ImageProvider.class, method = "update")
    int update(ImageInfo imageInfo);

    @SelectProvider(type = ImageProvider.class,method = "findById")
    ImageInfo findById(String id);
}
