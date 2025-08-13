package com.ruoyi.tools.utils.serializer;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * 序列化
 */
public class ProtostuffSerializer {
	private static final ThreadLocal<LinkedBuffer> BUFFER_THREAD_LOCAL = ThreadLocal
			.withInitial(() -> LinkedBuffer.allocate(512));


	/**
	 * @desc protostuff 目前不支持直接序列化List等对象，需要使用普通的POJO包装一下
	 */
	private static class SerializeData {
		private Object target;

		public Object getTarget() {
			return target;
		}
		public void setTarget(Object target) {
			this.target = target;
		}
	}
	/**
	 * @desc 序列化
	 */
	@SuppressWarnings("unchecked")
	public static byte[] serialize(Object obj) {
		SerializeData data = new SerializeData();
		data.setTarget(obj);
		Schema<SerializeData> schema = RuntimeSchema.getSchema((Class<SerializeData>) data.getClass());
		LinkedBuffer buffer = BUFFER_THREAD_LOCAL.get();
		try {
			return ProtostuffIOUtil.toByteArray(data, schema, buffer);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		} finally {
			buffer.clear();
		}
	}

	/**
	 * 
	 * @desc 反序列化
	 */
	@SuppressWarnings("unchecked")
	public static <T> T unSerialize(byte[] data) {
		Schema<SerializeData> schema = RuntimeSchema.getSchema(SerializeData.class);
		SerializeData message = schema.newMessage();
		ProtostuffIOUtil.mergeFrom(data, message, schema);

		return (T) message.getTarget();
	}

}
