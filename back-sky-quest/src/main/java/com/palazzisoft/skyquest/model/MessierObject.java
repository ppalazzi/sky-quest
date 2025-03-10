package com.palazzisoft.skyquest.model;

import java.util.List;

public record MessierObject(int messierNumber,
                            String name,
                            List<String> alternateNames,
                            String NGC,
                            String type,
                            String constellation,
                            String rightAscension,
                            String declination,
                            double magnitude,
                            String size,
                            int distance,
                            String viewingSeason,
                            String viewingDifficulty,
                            String image) {
}
