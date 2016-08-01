package by.training.hrsystem.domain.type;

public enum SkillType {
	NOVICE {
		{
			skillType = "novice";
		}
	},
	INTERMEDIATE {
		{
			skillType = "intermediate";
		}
	},
	ADVANCED {
		{
			skillType = "advanced";
		}
	},
	EXPERT {
		{
			skillType = "expert";
		}
	};

	String skillType;

	public String getSkillType() {
		return skillType;
	}

}
