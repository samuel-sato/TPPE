package unb.tppe.domain;

public enum UserRoleEnum {
    ADMIN(1),
    CLIENT(2),
    SELLER(3);

    public int value;

    UserRoleEnum(int value){
        this.value = value;
    }

    public static UserRoleEnum fromNumero(int numero) {
        for (UserRoleEnum roleEnum : UserRoleEnum.values()) {
            if (roleEnum.value == numero) {
                return roleEnum;
            }
        }
        throw new IllegalArgumentException("Número inválido para UserRoleEnum: " + numero);
    }
}
