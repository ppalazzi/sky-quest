package com.palazzisoft.skyquest.model;

import java.util.Map;

public record Messier(Info info, Map<String, MessierObject> data) {
}
