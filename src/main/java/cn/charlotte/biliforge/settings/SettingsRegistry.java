package cn.charlotte.biliforge.settings;

import cn.charlotte.biliforge.BiliForge;
import cn.charlotte.biliforge.util.render.colors.CustomColor;
import lombok.Getter;
import lombok.SneakyThrows;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Configuration;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SettingsRegistry {
    private static final Object NULL_OBJECT = new Object();
    private static Map<SettingKey<?>, Object> settings = new ConcurrentHashMap<>();

    @Getter
    private static Map<SettingInfo, List<SettingKey>> settingClasses = new ConcurrentHashMap<>();

    private Configuration configuration;

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;

        List<SettingKey<?>> keys = new ArrayList<>(settings.keySet());
        settings.clear();
        for (SettingKey key : keys) {
            register(key);
        }
    }

    @SneakyThrows
    public void register(Class<?> settingsClass) {
        if (!settingsClass.isAnnotationPresent(SettingInfo.class)) return;

        SettingInfo settingInfo = settingsClass.getAnnotation(SettingInfo.class);

        Object settingInstance = settingsClass.newInstance();
        Field instanceField = settingInstance.getClass().getField("INSTANCE");
        instanceField.set(null, settingInstance);
        List<SettingKey> settingsKeys = new ArrayList<>();
        for (Field field : settingsClass.getDeclaredFields()) {
            if (SettingKey.class.isAssignableFrom(field.getType())) {
                SettingKey settingKey = (SettingKey<?>) field.get(settingInstance);
                register(settingKey);
                settingsKeys.add(settingKey);
            }
        }

        settingClasses.put(settingInfo, settingsKeys);
    }
    public void register(SettingKey<?> key) {
        Object value;
        if (configuration != null) {
            if (key.getDefault() instanceof Boolean) {
                value = configuration.get(key.getCategory(), key.getKey(), (Boolean) key.getDefault()).getBoolean();
            } else if (key.getDefault() instanceof Integer) {
                value = configuration.get(key.getCategory(), key.getKey(), (Integer) key.getDefault()).getInt();
            } else if (key.getDefault() instanceof Double) {
                value = configuration.get(key.getCategory(), key.getKey(), (Double) key.getDefault()).getDouble();
            } else if (key.getDefault() instanceof String) {
                value = configuration.get(key.getCategory(), key.getKey(), (String) key.getDefault()).getString();
            } else if (key.getDefault() instanceof CustomColor) {
                String rgbaString = configuration.get(key.getCategory(), key.getKey(), key.getDefault().toString()).getString();
                String[] rgba = rgbaString.replace("rgba(", "").replace(")", "").split(",");
                value = new CustomColor(Float.valueOf(rgba[0]), Float.valueOf(rgba[1]), Float.valueOf(rgba[2]), Float.valueOf(rgba[3]));
            } else {
                throw new IllegalArgumentException("Default type " + key.getDefault().getClass() + " not supported.");
            }
        } else {
            value = NULL_OBJECT;
        }

        settings.put(key, value);
    }

    public Set<SettingKey<?>> getSettings() {
        return settings.keySet();
    }

    public Map<SettingInfo, List<SettingKey>> getSettingMappings() {
        return settingClasses;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(SettingKey<T> key) {
        if (!settings.containsKey(key)) {
            throw new IllegalArgumentException("Setting " + key + " unknown.");
        }
        return (T) settings.get(key);
    }

    public <T> void set(SettingKey<T> key, T value) {
        if (key.getDefault() instanceof Boolean) {
            configuration.get(key.getCategory(), key.getKey(), (Boolean) key.getDefault()).set((Boolean) value);
        } else if (key.getDefault() instanceof Integer) {
            configuration.get(key.getCategory(), key.getKey(), (Integer) key.getDefault()).set((Integer) value);
        } else if (key.getDefault() instanceof Double) {
            configuration.get(key.getCategory(), key.getKey(), (Double) key.getDefault()).set((Double) value);
        } else if (key.getDefault() instanceof Long) {
            configuration.get(key.getCategory(), key.getKey(), (Long) key.getDefault()).set((Long) value);
        } else if (key.getDefault() instanceof Float) {
            configuration.get(key.getCategory(), key.getKey(), (Float) key.getDefault()).set((Float) value);
        } else if (key.getDefault() instanceof String) {
            configuration.get(key.getCategory(), key.getKey(), (String) key.getDefault()).set((String) value);
        } else if (key.getDefault() instanceof CustomColor) {
            configuration.get(key.getCategory(), key.getKey(), key.getDefault().toString()).set(value.toString());
        } else {
            throw new IllegalArgumentException("Default type " + key.getDefault().getClass() + " not supported.");
        }
        settings.put(key, value);
    }

    public void save() {
        configuration.save();
    }

    public interface SettingKey<T> {
        String getCategory();

        String getKey();

        String getDisplayString();

        String getDescription();

        T getDefault();

        default void onChanged() {
        }

        default T getValue() {
            return BiliForge.getInstance().getSettingsRegistry().get(this);
        }

        default void setValue(T value) {
            BiliForge.getInstance().getSettingsRegistry().set(this, value);
        }
    }

    public interface MultipleChoiceSettingKey<T> extends SettingKey<T> {
        List<T> getChoices();
    }

    public static class SettingKeys<T> implements SettingKey<T> {
        private final String category;
        private final String key;
        private final String displayString;
        private final T defaultValue;
        private final String description;

        public SettingKeys(String category, String key, String displayString, String description, T defaultValue) {
            this.category = category;
            this.key = key;
            this.displayString = displayString;
            this.description = description;
            this.defaultValue = defaultValue;
        }

        @Override
        public String getCategory() {
            return category;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public String getDisplayString() {
            return displayString == null ? null : I18n.format(displayString);
        }

        @Override
        public T getDefault() {
            return defaultValue;
        }

        @Override
        public String getDescription() {
            return description;
        }

    }

    public static class MultipleChoiceSettingKeys<T> extends SettingKeys<T> implements MultipleChoiceSettingKey<T> {
        private List<T> choices = Collections.emptyList();

        public MultipleChoiceSettingKeys(String category, String key, String displayString, String description, T defaultValue) {
            super(category, key, displayString, description, defaultValue);
        }

        @Override
        public List<T> getChoices() {
            return choices;
        }

        public void setChoices(List<T> choices) {
            this.choices = Collections.unmodifiableList(choices);
        }
    }
}